import React from 'react';
import PropTypes from 'prop-types';

import { Content } from '@xtraplatform/core';
import ServiceEditHeader from './Header';
import { useChecks, useEntities } from '../../hooks';
import { useParams } from 'react-router-dom';
import TabsOption from './TabsOptions';

const Details = () => {
    const { id: currentID } = useParams();
    const currentEntityId = currentID.split('-')[0];
    const currentEntityKey = currentID.split('-')[1];
    const entities = useEntities();
    const healthchecks = useChecks();
    const selectedChecks = Object.keys(healthchecks).filter((key) => key.includes(currentEntityId));

    const service = currentEntityId ? currentEntityId : {};
    const provider = entities[currentEntityKey].find((entity) => entity.id === currentEntityId);

    const status = provider
        ? provider.status.charAt(0).toUpperCase() + provider.status.substring(1).toLowerCase()
        : 'DONTKNOW';

    return (
        <Content
            header={<ServiceEditHeader service={service} status={status} />}
            main={
                <TabsOption
                    currentID={currentEntityId}
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
