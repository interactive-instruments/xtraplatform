import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

import { Moon, Sun } from 'grommet-icons';
import { Box, Button, Grid, grommet, Grommet, Header, Page, PageContent, Text } from 'grommet';
import { deepMerge } from 'grommet/utils';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from '../Tile';

const theme = deepMerge(grommet, {
    global: {
        colors: {
            brand: '#228BE6',
        },
        font: {
            family: 'Roboto',
            size: '14px',
            height: '20px',
        },
    },
    normalizeColor: (color) => {
        return color;
    },
});

const AppBar = (props) => (
    <Header
        background='brand'
        pad={{ left: 'medium', right: 'small', vertical: 'small' }}
        elevation='small'
        {...props}
    />
);

const Details = ({ currentID }) => {
    const [dark, setDark] = useState(false);
    const [healthchecks, setHealthchecks] = useState({});
    const selectedChecks = Object.keys(healthchecks).filter((key) => key.includes(currentID));

    useEffect(() => {
        fetch('healthcheck')
            .then((response) => {
                console.log(response.status);
                return response.json();
            })
            .then((data) => {
                console.log(data);
                setHealthchecks(data);
            })
            .catch((error) => console.log(error));
    }, []);

    console.log(currentID, selectedChecks);

    return (
        <Grommet theme={theme} full themeMode={dark ? 'dark' : 'light'}>
            <Page>
                <AppBar>
                    <Text size='large'>{currentID}</Text>
                    <Button
                        a11yTitle={dark ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                        icon={dark ? <Moon /> : <Sun />}
                        onClick={() => setDark(!dark)}
                        tip={{
                            content: (
                                <Box
                                    pad='small'
                                    round='small'
                                    background={dark ? 'dark-1' : 'light-3'}>
                                    {dark ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                                </Box>
                            ),
                            plain: true,
                        }}
                    />
                </AppBar>
                <PageContent>
                    <Grid columns='medium' gap='large' pad={{ bottom: 'large' }}>
                        {selectedChecks && selectedChecks.length > 0 ? (
                            selectedChecks.map((check) => (
                                <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                                    <Box pad='none' background='content' flex={false}>
                                        <TileGrid compact='small'>
                                            <Tile
                                                title={
                                                    check
                                                        ? `${check
                                                              .substring(check.lastIndexOf('.') + 1)
                                                              .replace(
                                                                  /([a-z])([A-Z])/g,
                                                                  '$1 $2'
                                                              )}: `
                                                        : 'No pending Checks'
                                                }
                                                status={healthchecks[check]?.healthy}
                                                key={currentID}
                                                isCompact
                                            />
                                        </TileGrid>
                                    </Box>
                                </Box>
                            ))
                        ) : (
                            <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                                <Box pad='none' background='content' flex={false}>
                                    <TileGrid compact='small'>
                                        <Tile title='No pending Checks' key={currentID} isCompact />
                                    </TileGrid>
                                </Box>
                            </Box>
                        )}
                    </Grid>
                </PageContent>
            </Page>
        </Grommet>
    );
};

Details.displayName = 'Details';

Details.propTypes = {
    currentID: PropTypes.string.isRequired,
};

Details.defaultProps = {};

export default Details;
