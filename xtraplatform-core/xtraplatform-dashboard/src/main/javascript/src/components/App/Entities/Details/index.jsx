import React from 'react';
import PropTypes from 'prop-types';

import { Content } from '@xtraplatform/core';
import ServiceEditHeader from './Header';
import { useChecks, useEntities } from '../../hooks';
import { useParams } from 'react-router-dom';
import TabsOption from './TabsOptions';

const Details = () => {
    const { id: currentID } = useParams();
    const entities = useEntities();
    const healthchecks = useChecks();
    const selectedChecks = Object.keys(healthchecks).filter((key) => key.includes(currentID));

    const service = currentID ? currentID : {};
    const provider = entities.providers.find((provider) => provider.id === currentID);
    const status = provider
        ? provider.status.charAt(0).toUpperCase() + provider.status.substring(1).toLowerCase()
        : 'DONTKNOW';

    return (
        <Content
            header={<ServiceEditHeader service={service} status={status} />}
            main={
                <TabsOption
                    currentID={currentID}
                    healthchecks={healthchecks}
                    selectedChecks={selectedChecks}
                />
            }
        />
    );
};

Details.displayName = 'Details';

Details.propTypes = {
    currentID: PropTypes.string.isRequired,
};

Details.defaultProps = {};

export default Details;
