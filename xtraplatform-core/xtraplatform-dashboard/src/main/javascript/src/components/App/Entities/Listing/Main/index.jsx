import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { Box, Grid } from 'grommet';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from './Tile';

const EntitiesListing = ({ entities }) => {
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
                            label='Provider'
                            status={
                                provider.status.charAt(0).toUpperCase() +
                                provider.status.substring(1).toLowerCase()
                            }
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
