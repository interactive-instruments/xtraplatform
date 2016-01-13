/**
 * Copyright 2016 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ii.xsf.core.api.exceptions;

import java.util.Locale;
import org.forgerock.i18n.LocalizableMessageDescriptor;

/**
 *
 * @author fischer
 */
public class MessageCompiler {
            
    public static String compileMessage(Object m, Object... args) {
        Locale locale = new Locale("en");
        if (args.length == 0) {
            return ((LocalizableMessageDescriptor.Arg0) m).get().toString(locale);
        } else if (args.length == 1) {
            return ((LocalizableMessageDescriptor.Arg1) m).get(args[0]).toString(locale);
        } else if (args.length == 2) {
            return ((LocalizableMessageDescriptor.Arg2) m).get(args[0], args[1]).toString(locale);
        } else if (args.length == 3) {
            return ((LocalizableMessageDescriptor.Arg3) m).get(args[0], args[1], args[2]).toString(locale);
        } else if (args.length == 4) {
            return ((LocalizableMessageDescriptor.Arg4) m).get(args[0], args[1], args[2], args[3]).toString(locale);
        } else if (args.length == 5) {
            return ((LocalizableMessageDescriptor.Arg5) m).get(args[0], args[1], args[2], args[3], args[4]).toString(locale);
        } else if (args.length == 6) {
            return ((LocalizableMessageDescriptor.Arg6) m).get(args[0], args[1], args[2], args[3], args[4], args[5]).toString(locale);
        } else if (args.length == 7) {
            return ((LocalizableMessageDescriptor.Arg7) m).get(args[0], args[1], args[2], args[3], args[4], args[5], args[6]).toString(locale);
        } else if (args.length == 8) {
            return ((LocalizableMessageDescriptor.Arg8) m).get(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]).toString(locale);
        } else if (args.length == 9) {
            return ((LocalizableMessageDescriptor.Arg9) m).get(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]).toString(locale);
        } else {
            return ((LocalizableMessageDescriptor.ArgN) m).get(args).toString(locale);
        }
    }
}
