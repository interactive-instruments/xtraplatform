import React from 'react';
import { Grommet } from 'grommet';
import { createTheme } from '../xtraplatform-core/xtraplatform-manager/src/main/javascript/core';

export const decorators = [
    (Story) => (
        <Grommet theme={createTheme()}>
            <Story />
        </Grommet>
    ),
];

export const parameters = {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: { expanded: true },
};
