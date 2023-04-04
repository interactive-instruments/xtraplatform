import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

import { Box, ResponsiveContext, Grid, Flex } from 'grommet';

import { TileGrid, Content } from '@xtraplatform/core';
import { Tile } from './Tile';
import { useEntities } from '../../../hooks';

const EntitiesListing = ({}) => {
    const [currentID, setCurrentID] = useState(null);
    const entities = useEntities();

    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Grid
                columns={{ count: 'fit', size: ['small', 'medium'] }}
                gap='large'
                pad={{ bottom: 'small', top: 'small', left: '9%' }}>
                {entities.providers.map((provider) => (
                    <TileGrid compact='small'>
                        <Tile
                            key={provider.id}
                            title={provider.id}
                            status={
                                provider.status.charAt(0).toUpperCase() +
                                provider.status.substring(1).toLowerCase()
                            }
                            setCurrentID={setCurrentID}
                            currentID={currentID}
                            id={provider.id}
                            isCompact
                        />
                    </TileGrid>
                ))}
            </Grid>
        </Box>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
