import React, { useState } from 'react';

import { Moon, Sun } from 'grommet-icons';
import { Box, Button, Grid, grommet, Grommet, Header, Page, PageContent, Text } from 'grommet';
import { deepMerge } from 'grommet/utils';

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

const ListingHeader = () => {
    const [dark, setDark] = useState(false);

    return (
        <Grommet theme={theme} full themeMode={dark ? 'dark' : 'light'}>
            <Page>
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
            </Page>
        </Grommet>
    );
};

export default ListingHeader;
