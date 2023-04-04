import React from 'react';
import PropTypes from 'prop-types';
// import { useFassets } from 'feature-u';

import { Box, Grid } from 'grommet';
import { Tile } from '../../Listing/Main/Tile';
import { TileGrid } from '@xtraplatform/core';

const Main = ({ selectedChecks, healthchecks, currentID }) => {
    return (
        <Grid columns='medium' gap='large' pad={{ bottom: 'large' }}>
            <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                <Box pad='none' background='content' flex={false}>
                    <TileGrid compact='small'>
                        {selectedChecks && selectedChecks.length > 0 ? (
                            selectedChecks.map((check) => (
                                <Tile
                                    title={
                                        check
                                            ? `${check
                                                  .substring(check.lastIndexOf('.') + 1)
                                                  .replace(/([a-z])([A-Z])/g, '$1 $2')}: `
                                            : 'No pending Checks'
                                    }
                                    status={healthchecks[check]?.healthy}
                                    key={currentID}
                                    isCompact
                                />
                            ))
                        ) : (
                            <Tile title='No pending Checks' key={currentID} isCompact />
                        )}
                    </TileGrid>
                </Box>
            </Box>
        </Grid>
    );
};

Main.displayName = 'Main';

Main.propTypes = {
    compact: PropTypes.bool,
    role: PropTypes.string,
};

export default Main;
