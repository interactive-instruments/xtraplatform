import React from 'react';

import { Moon, Sun } from 'grommet-icons';
import { Box, Button, Header, Text } from 'grommet';

const AppBar = (props) => (
    <Header
        background='brand'
        pad={{ left: 'medium', right: 'small', vertical: 'small' }}
        style={{ height: '95px' }}
        elevation='small'
        {...props}
    />
);

const ClusterHeader = ({ dark, setDark }) => {
    return (
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
    );
};

export default ClusterHeader;
