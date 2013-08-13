/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.hql.ast.spi.predicate;

/**
 * An comparison predicate such as {@code EQUALS} or {@code LESS}.
 *
 * @author Gunnar Morling
 * @author Sanne Grinovero
 */
public abstract class ComparisonPredicate<Q> extends AbstractPredicate<Q> {

	public enum Type {
		LESS {
			@Override
			<Q> Q getQuery(ComparisonPredicate<Q> predicate) {
				return predicate.getStrictlyLessQuery();
			}
		},
		LESS_OR_EQUAL {
			@Override
			<Q> Q getQuery(ComparisonPredicate<Q> predicate) {
				return predicate.getLessOrEqualsQuery();
			}
		},
		EQUALS {
			@Override
			<Q> Q getQuery(ComparisonPredicate<Q> predicate) {
				return predicate.getEqualsQuery();
			}
		},
		GREATER_OR_EQUAL {
			@Override
			<Q> Q getQuery(ComparisonPredicate<Q> predicate) {
				return predicate.getGreaterOrEqualsQuery();
			}
		},
		GREATER {
			@Override
			<Q> Q getQuery(ComparisonPredicate<Q> predicate) {
				return predicate.getStrictlyGreaterQuery();
			}
		};

		abstract <Q> Q getQuery(ComparisonPredicate<Q> predicate);

	}

	protected final String propertyName;
	protected final Type type;
	protected final Object value;

	public ComparisonPredicate(String propertyName, Type type, Object value) {
		super( Predicate.Type.COMPARISON );
		this.propertyName = propertyName;
		this.type = type;
		this.value = value;
	}

	@Override
	public Q getQuery() {
		return type.getQuery( this );
	}

	protected abstract Q getStrictlyLessQuery();

	protected abstract Q getLessOrEqualsQuery();

	protected abstract Q getEqualsQuery();

	protected abstract Q getGreaterOrEqualsQuery();

	protected abstract Q getStrictlyGreaterQuery();

	@Override
	public String toString() {
		return "( " + type + " " + propertyName + " " + value + " )";
	}
}
