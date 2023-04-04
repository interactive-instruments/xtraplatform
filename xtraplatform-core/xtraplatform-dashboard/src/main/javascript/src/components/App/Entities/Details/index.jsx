import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

import { Moon, Sun } from 'grommet-icons';
import { Box, Button, Grid, grommet, Grommet, Header, Page, PageContent, Text } from 'grommet';
import { deepMerge } from 'grommet/utils';

import { TileGrid, Content } from '@xtraplatform/core';
import ServiceEditHeader from './Header';
import { Tile } from '../Listing/Main/Tile';
import { useEntities } from '../../hooks';
import { useParams } from 'react-router-dom';
import Main from './Main';

const Details = () => {
    const { id: currentID } = useParams();
    const entities = useEntities();
    const [healthchecks, setHealthchecks] = useState({});
    const selectedChecks = Object.keys(healthchecks).filter((key) => key.includes(currentID));

    useEffect(() => {
        fetch('healthcheck')
            .then((response) => {
                console.log(response.status);
                return response.json();
            })
            .then((data) => {
                console.log(data);
                setHealthchecks(data);
            })
            .catch((error) => console.log(error));
    }, []);

    const service = currentID ? currentID : {};
    const currentProvider = entities.providers.find((p) => p.id === currentID);
    const status = currentProvider ? currentProvider.status : undefined;

    return (
        <Content
            header={
                <ServiceEditHeader
                    selectedChecks={selectedChecks}
                    status={status}
                    service={service}
                />
            }
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
