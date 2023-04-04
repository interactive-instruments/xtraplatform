import React, { useState } from 'react';

import { Moon, Sun } from 'grommet-icons';
import { Box, Button, Grid, grommet, Grommet, Header, Page, PageContent, Text } from 'grommet';
import { deepMerge } from 'grommet/utils';

import { TileGrid } from '@xtraplatform/core';
import services from './services';
import healthcheck from './healthcheck';
import { Tile } from '../Entities/Listing/Main/Tile';

const AppBar = (props) => (
    <Header
        background='brand'
        pad={{ left: 'medium', right: 'small', vertical: 'small' }}
        style={{ height: '95px' }}
        elevation='small'
        {...props}
    />
);

const Details = ({ dark, setDark, theme }) => {
    const providers = services.providers.map((provider) => {
        return provider.id;
    });
    const unsortedChecks = Object.keys(healthcheck)
        .filter((key) => {
            return !providers.some((provider) => key.includes(provider));
        })
        .map((key) => key.substring(0, 10));

    return (
        <Page>
            <AppBar>
                <Text size='large'>Other Checks</Text>
                <Button
                    a11yTitle={dark ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                    icon={dark ? <Moon /> : <Sun />}
                    onClick={() => setDark(!dark)}
                    tip={{
                        content: (
                            <Box pad='small' round='small' background={dark ? 'dark-1' : 'light-3'}>
                                {dark ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                            </Box>
                        ),
                        plain: true,
                    }}
                />
            </AppBar>

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
        </Page>
    );
};

export default Details;
