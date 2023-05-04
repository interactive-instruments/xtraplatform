import React from 'react';

import { Box, Grid } from 'grommet';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from '../../Entities/Listing/Main/Tile';

const ClusterMain = ({ unsortedChecks, healthcheck }) => {
    const status = unsortedChecks.every((check) => healthcheck[check]?.healthy);

    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Grid
                columns={{ count: 'fit', size: ['small', 'medium'] }}
                gap='large'
                pad={{ bottom: 'small', top: 'small', left: '9%' }}>
                <TileGrid compact='small'>
                    <Tile
                        title={'Localhost'}
                        status={status}
                        key={'Localhost'}
                        id={'localhost'}
                        isCompact
                    />
                </TileGrid>
            </Grid>
        </Box>
    );
};
export default ClusterMain;
