import React from 'react';
import PropTypes from 'prop-types';

import { Box, Grid, Table, TableHeader, TableRow, TableCell, TableBody } from 'grommet';
import { Tile } from '../../Listing/Main/Tile';
import getStatusIcon from '../../../../Icon';

const Main = ({ selectedChecks, healthchecks, currentID }) => {
    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Grid
                columns={{ count: 'fit', size: ['small', 'medium'] }}
                gap='large'
                pad={{ bottom: 'medium', top: 'medium', left: 'medium', right: 'medium' }}>
                {selectedChecks && selectedChecks.length > 0 ? (
                    <Table>
                        <TableHeader>
                            <TableRow>
                                <TableCell scope='col' border='bottom'>
                                    Name
                                </TableCell>
                                <TableCell scope='col' border='bottom'>
                                    Status
                                </TableCell>
                                <TableCell scope='col' border='bottom'>
                                    Date
                                </TableCell>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {selectedChecks.map((check) => (
                                <TableRow key={check}>
                                    <TableCell scope='row'>
                                        <strong>
                                            {check
                                                ? `${check
                                                      .substring(check.lastIndexOf('.') + 1)
                                                      .replace(/([a-z])([A-Z])/g, '$1 $2')} `
                                                : 'No pending Checks'}
                                        </strong>
                                    </TableCell>
                                    <TableCell>
                                        {healthchecks[check]
                                            ? getStatusIcon(healthchecks[check]?.healthy)
                                            : ''}
                                    </TableCell>
                                    <TableCell>{healthchecks[check]?.timestamp}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
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
