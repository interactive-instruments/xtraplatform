import React from 'react';
import { useEntities } from '../../../hooks';
import getStatusIcon from '../../../../Icon';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from '../Main/Tile';

import {
    Box,
    Card,
    CardBody,
    CardFooter,
    Cards,
    DataFilters,
    DataFilter,
    DataSearch,
    DataSummary,
    Grid,
    Heading,
    Toolbar,
    Data,
} from 'grommet';

const Filter = () => {
    const entities = useEntities();
    const DATA = entities.providers.map((provider) => {
        return {
            title: provider.id,
            label: 'Provider',
            status:
                provider.status.charAt(0).toUpperCase() +
                provider.status.substring(1).toLowerCase(),
            id: provider.id,
        };
    });

    return (
        <Grid
            flex={false}
            pad='large'
            columns={[['small', 'xlarge']]}
            justifyContent='center'
            gap='large'>
            <Data data={DATA}>
                <Toolbar>
                    <DataSearch />
                    <DataFilters drop>
                        <DataFilter property='location' />
                    </DataFilters>
                </Toolbar>
                <DataSummary />
                <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
                    <Grid
                        columns={{ count: 'fit', size: ['small', 'medium'] }}
                        gap='large'
                        pad={{ bottom: 'small', top: 'small', left: '9%' }}>
                        <Cards>
                            {(item) => (
                                <TileGrid compact='small'>
                                    <Tile
                                        key={item.title}
                                        title={item.title}
                                        label='Provider'
                                        status={item.status}
                                        id={item.title}
                                        isCompact
                                    />
                                </TileGrid>
                            )}
                        </Cards>
                    </Grid>
                </Box>
            </Data>
        </Grid>
    );
};
Filter.storyName = 'Cards';

Filter.args = {
    full: true,
};

export default Filter;
