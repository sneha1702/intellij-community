/*
 * Copyright 2000-2008 JetBrains s.r.o.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.plugins.groovy.lang.completion.handlers;

import com.intellij.codeInsight.completion.CompletionContext;
import com.intellij.codeInsight.completion.LookupData;
import com.intellij.codeInsight.lookup.LookupItem;

/**
 * @author ilyas
 */
public interface ContextSpecificInsertHandler {

  boolean isAcceptable(CompletionContext context, int startOffset, LookupData data, LookupItem item, boolean signatureSelected, char completionChar);

  /**
   * Handler can be executed ONLY if {@link #isAcceptable(CompletionContext, int, LookupData, LookupItem, boolean, char)}
   * returns true.
   */
  void handleInsert(CompletionContext context, int startOffset, LookupData data, LookupItem item, boolean signatureSelected, char completionChar);
}
