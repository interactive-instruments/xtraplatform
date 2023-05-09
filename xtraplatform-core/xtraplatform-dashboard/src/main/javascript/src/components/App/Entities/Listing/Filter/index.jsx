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

    const labels = [];
    DATA.filter((item) => {
        if (!labels.includes(item.label)) {
            labels.push(item.label);
        }
    });

    let tileGridColumns;
    if (filteredData.length <= 3) {
        tileGridColumns = ['flex'];
    } else {
        const numColumns = Math.ceil(filteredData.length / Math.ceil(filteredData.length / 3));
        tileGridColumns = new Array(numColumns).fill('flex');
    }

    return (
        <Box gap='large' pad='medium'>
            <Data data={filteredData}>
                <Toolbar>
                    <DataSearch />
                    <DataFilters drop>
                        <DataFilter
                            property='label'
                            options={labels}
                            selected={selectedFilter}
                            onSelect={(event) => setSelectedFilter(event.option)}
                        />
                    </DataFilters>
                </Toolbar>
                <DataSummary style={{ marginBottom: '25px' }} />
                <Cards size={tileGridColumns}>
                    {(item) => (
                        <TileGrid compact='small'>
                            <Tile
                                key={item.title}
                                title={item.title}
                                label={item.label}
                                status={item.status}
                                id={item.title + '-' + item.label}
                                isCompact
                            />
                        </TileGrid>
                    )}
                </Cards>
            </Data>
        </Box>
    );
};
Filter.storyName = 'Cards';

Filter.args = {
    full: true,
};

export default Filter;
