/*
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
define([
    "dojo/_base/declare"
],
        function(declare) {
            return declare([], {
                
                getMainMenuItems: function() {
                    return [];
                },
                        
                getTopMenuItems: function() {
                    return [];
                },
                        
                getRightFooterItems: function() {
                    return [];
                },
                        
                getSettingsItems: function() {
                    return [];
                },
                
                getSecurityItems: function() {
                    return [];
                },
                
                getLogItems: function() {
                    return [];
                },
                        
                getServiceSettingsItems: function() {
                    return [];
                },
                        
                getServiceViews: function() {
                    return [];
                },
                        
                getServiceActions: function() {
                    return [];
                },
                
                applyStoreMixins: function(store) {
                    return store;
                },
                
                getManualProvider: function() {
                    return null;
                }
            });
        });