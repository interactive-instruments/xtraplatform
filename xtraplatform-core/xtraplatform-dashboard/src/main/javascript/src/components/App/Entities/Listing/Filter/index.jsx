import React from 'react';

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

const Filter = ({ DATA }) => {
    let gridColumns;
    if (DATA.length <= 3) {
        gridColumns = 'center';
    } else if (DATA.length <= 6) {
        gridColumns = 'two';
    } else {
        gridColumns = 'three';
    }

    let tileGridColumns;
    if (DATA.length <= 3) {
        tileGridColumns = ['large'];
    } else if (DATA.length <= 6) {
        tileGridColumns = ['medium', 'medium'];
    } else {
        tileGridColumns = ['small', 'small', 'small'];
    }

    return (
        <Box gap='large' pad='medium'>
            <Data data={DATA}>
                <Toolbar>
                    <DataSearch />
                    <DataFilters drop>
                        <DataFilter property='label' options={['Provider', 'Services']} />
                    </DataFilters>
                </Toolbar>
                <DataSummary />
                <Grid
                    columns={{ count: gridColumns, size: tileGridColumns }}
                    gap='large'
                    pad='small'
                    alignSelf='center'>
                    <Cards alignSelf='center' size={tileGridColumns}>
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
            </Data>
        </Box>
    );
};
Filter.storyName = 'Cards';

Filter.args = {
    full: true,
};

export default Filter;
