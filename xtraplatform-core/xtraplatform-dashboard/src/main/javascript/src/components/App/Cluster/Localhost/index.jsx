import React from 'react';

import { Content } from '@xtraplatform/core';
import LocalhostHeader from './Header';
import { useChecks, useEntities } from '../../hooks';
import TabsOption from './TabsOptions';

const LocalhostTabs = () => {
    const entities = useEntities();
    const healthcheck = useChecks();
    const providers = entities.providers.map((provider) => {
        return provider.id;
    });
    const unsortedChecks = Object.keys(healthcheck)
        .filter((key) => {
            return !providers.some((provider) => key.includes(provider));
        })
        .map((key) => key.substring(0, 10));
    const status = unsortedChecks.every((check) => healthcheck[check]?.healthy);

    return (
        <Content
            header={<LocalhostHeader status={status} />}
            main={<TabsOption healthcheck={healthcheck} unsortedChecks={unsortedChecks} />}
        />
    );
};

LocalhostTabs.displayName = 'LocalhostTabs';

LocalhostTabs.defaultProps = {};

export default LocalhostTabs;
