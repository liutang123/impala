// Copyright 2013 Cloudera Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cloudera.impala.authorization;

import java.util.EnumSet;

/*
 * Maps an Impala Privilege to one or more Hive Access "Actions".
 */
public enum Privilege {
  ALL(org.apache.access.core.Action.ALL, false),
  ALTER(org.apache.access.core.Action.ALL, false),
  DROP(org.apache.access.core.Action.ALL, false),
  CREATE(org.apache.access.core.Action.ALL, false),
  INSERT(org.apache.access.core.Action.INSERT, false),
  SELECT(org.apache.access.core.Action.SELECT, false),
  // Privileges required to view metadata on a server object.
  VIEW_METADATA(EnumSet.of(
      org.apache.access.core.Action.INSERT,
      org.apache.access.core.Action.SELECT), true),
  // Special privilege that is used to determine if the user has any valid privileges
  // on a target object.
  ANY(EnumSet.allOf(org.apache.access.core.Action.class), true),
  ;

  private final EnumSet<org.apache.access.core.Action> actions;

  // Determines whether to check if the user has ANY the privileges defined in the
  // actions list or whether to check if the user has ALL of the privileges in the
  // actions list.
  private final boolean anyOf;

  private Privilege(EnumSet<org.apache.access.core.Action> actions, boolean anyOf) {
    this.actions = actions;
    this.anyOf = anyOf;
  }

  private Privilege(org.apache.access.core.Action action, boolean anyOf) {
    this(EnumSet.of(action), anyOf);
  }

  /*
   * Returns the set of Hive Access Actions mapping to this Privilege.
   */
  public EnumSet<org.apache.access.core.Action> getHiveActions() {
    return actions;
  }

  /*
   * Determines whether to check if the user has ANY the privileges defined in the
   * actions list or whether to check if the user has ALL of the privileges in the
   * actions list.
   */
  public boolean getAnyOf() {
    return anyOf;
  }
}