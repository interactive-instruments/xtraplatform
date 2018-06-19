/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xsf.cfgstore.api;

/**
 * @author zahnen
 */
public class ConfigValueDescriptor {
    private final String label;
    private final String description;
    private final String validator;

    public ConfigValueDescriptor(String label, String description, String validator) {
        this.label = label;
        this.description = description;
        this.validator = validator;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getValidator() {
        return validator;
    }
}