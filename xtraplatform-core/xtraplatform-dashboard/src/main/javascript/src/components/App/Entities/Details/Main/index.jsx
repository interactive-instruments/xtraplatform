import React from 'react';
import PropTypes from 'prop-types';

import { Box, Grid } from 'grommet';
import { Tile } from '../../Listing/Main/Tile';

const Main = ({ selectedChecks, healthchecks, currentID }) => {
    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Grid
                columns={{ count: 'fit', size: ['small', 'medium'] }}
                gap='large'
                pad={{ bottom: 'medium', top: 'medium', left: 'medium', right: 'medium' }}>
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
            </Grid>
        </Box>
    );
};

Main.displayName = 'Main';

Main.propTypes = {
    compact: PropTypes.bool,
    role: PropTypes.string,
};

export default Main;
