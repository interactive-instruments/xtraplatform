import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Moon, Sun } from 'grommet-icons';
import { Box, Button, Flex, grommet, Grommet, Header, Page, PageContent, Text } from 'grommet';

import { TileGrid, Content } from '@xtraplatform/core';
import Main from './Main';

import { deepMerge } from 'grommet/utils';

const AppBar = (props) => (
    <Header
        background='brand'
        style={{ width: '100%' }}
        pad={{ left: 'medium', right: 'small', vertical: 'small' }}
        {...props}
    />
);

const EntitiesListing = ({ dark, setDark }) => {
    return (
        <Page>
            <Content
                header={
                    <AppBar>
                        <Text size='large'>Services</Text>
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
                }
                main={<Main />}
            />
        </Page>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
