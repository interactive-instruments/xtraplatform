import React from 'react';

import { Box, Grid } from 'grommet';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from '../../Entities/Listing/Main/Tile';

const ClusterMain = ({ unsortedChecks, healthcheck }) => {
    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Grid
                columns={{ count: 'fit', size: ['small', 'medium'] }}
                gap='large'
                pad={{ bottom: 'small', top: 'small', left: '9%' }}>
                {unsortedChecks.map((check) => (
                    <TileGrid compact='small'>
                        <Tile
                            title={check}
                            status={healthcheck[check]?.healthy}
                            key={check}
                            isCompact
                        />
                    </TileGrid>
                ))}
            </Grid>
        </Box>
    );
};
export default ClusterMain;
