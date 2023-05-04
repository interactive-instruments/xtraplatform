import React from 'react';
import { Box, Grid, Table, TableHeader, TableRow, TableCell, TableBody } from 'grommet';
import { Tile } from '../../../Entities/Listing/Main/Tile';
import getStatusIcon from '../../../../Icon';

const Localhost = ({ healthcheck, unsortedChecks }) => {
    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Grid
                columns={{ count: 'fit', size: ['large'] }}
                gap='large'
                pad={{ bottom: 'medium', top: 'medium', left: 'medium', right: 'medium' }}>
                {unsortedChecks && unsortedChecks.length > 0 ? (
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
                            {unsortedChecks.map((check) => (
                                <TableRow key={check}>
                                    <TableCell scope='row'>
                                        <strong>{check ? `${check}` : 'No pending Checks'}</strong>
                                    </TableCell>
                                    <TableCell>
                                        {healthcheck[check]
                                            ? getStatusIcon(healthcheck[check]?.healthy)
                                            : ''}
                                    </TableCell>
                                    <TableCell>{healthcheck[check]?.timestamp}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                ) : (
                    <Tile title='No pending Checks' key={'No pending Checks'} isCompact />
                )}
            </Grid>
        </Box>
    );
};
export default Localhost;
