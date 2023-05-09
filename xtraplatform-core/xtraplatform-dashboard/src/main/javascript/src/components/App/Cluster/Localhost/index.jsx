import React from 'react';

import { Content } from '@xtraplatform/core';
import LocalhostHeader from './Header';
import { useChecks, useEntities } from '../../hooks';
import TabsOption from './TabsOptions';
import { Page } from 'grommet';

const LocalhostTabs = () => {
    const entities = useEntities();
    const healthcheck = useChecks();
    const providers = Object.values(entities)
        .filter(Array.isArray)
        .map((array) => array.map((item) => item.id))
        .flat();

    const unsortedChecks = Object.keys(healthcheck).filter((key) => {
        const keyParts = key.split('.');
        return keyParts.every((part) => !providers.includes(part));
    });

    const status = unsortedChecks.every((check) => healthcheck[check]?.healthy);

    return (
        <Page>
            <Content
                header={<LocalhostHeader status={status} />}
                main={<TabsOption healthcheck={healthcheck} unsortedChecks={unsortedChecks} />}
            />
        </Page>
    );
};

LocalhostTabs.displayName = 'LocalhostTabs';

LocalhostTabs.defaultProps = {};

export default LocalhostTabs;
