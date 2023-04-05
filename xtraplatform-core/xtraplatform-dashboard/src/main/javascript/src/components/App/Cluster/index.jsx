import React from 'react';

import { Page } from 'grommet';
import ClusterMain from './Main';
import { useChecks, useEntities } from '../hooks';
import ClusterHeader from './Header';
import Content from '../Entities/Listing/Content';

const Details = ({ dark, setDark }) => {
    const entities = useEntities();
    const healthchecks = useChecks();
    const providers = entities.providers.map((provider) => {
        return provider.id;
    });
    const unsortedChecks = Object.keys(healthchecks)
        .filter((key) => {
            return !providers.some((provider) => key.includes(provider));
        })
        .map((key) => key.substring(0, 10));

    return (
        <Page>
            <Content
                header={<ClusterHeader dark={dark} setDark={setDark} />}
                main={<ClusterMain unsortedChecks={unsortedChecks} healthcheck={healthchecks} />}
            />
        </Page>
    );
};

export default Details;
