import React, { useState } from 'react';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from '../Main/Tile';

import {
    Box,
    Cards,
    DataFilters,
    DataFilter,
    DataSearch,
    DataSummary,
    Grid,
    Toolbar,
    Data,
} from 'grommet';

const Filter = ({ DATA }) => {
    const [selectedFilter, setSelectedFilter] = useState('');

    let filteredData = DATA;
    if (selectedFilter) {
        filteredData = DATA.filter((item) => item.label === selectedFilter);
    }

    let gridColumns;
    if (filteredData.length <= 3) {
        gridColumns = 'center';
    } else if (filteredData.length <= 6) {
        gridColumns = 'two';
    } else {
        gridColumns = 'three';
    }

    let tileGridColumns;
    if (filteredData.length <= 3) {
        tileGridColumns = ['large'];
    } else if (filteredData.length <= 6) {
        tileGridColumns = ['medium', 'medium'];
    } else {
        tileGridColumns = ['small', 'small', 'small'];
    }

    return (
        <Box gap='large' pad='medium'>
            <Data data={filteredData}>
                <Toolbar>
                    <DataSearch />
                    <DataFilters drop>
                        <DataFilter
                            property='label'
                            options={['Provider', 'Services']}
                            selected={selectedFilter}
                            onSelect={(event) => setSelectedFilter(event.option)}
                        />
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
                                    label={item.label}
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
