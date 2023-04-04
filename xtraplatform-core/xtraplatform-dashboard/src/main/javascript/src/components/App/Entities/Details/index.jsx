import React from 'react';
import PropTypes from 'prop-types';

import { Content } from '@xtraplatform/core';
import ServiceEditHeader from './Header';
import { useChecks } from '../../hooks';
import { useParams } from 'react-router-dom';
import Main from './Main';

const Details = () => {
    const { id: currentID } = useParams();
    const healthchecks = useChecks();
    const selectedChecks = Object.keys(healthchecks).filter((key) => key.includes(currentID));

    const service = currentID ? currentID : {};

    return (
        <Content
            header={<ServiceEditHeader service={service} />}
            main={
                <Main
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
