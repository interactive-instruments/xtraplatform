import React from 'react';

import { Moon, Sun } from 'grommet-icons';
import { Box, Button } from 'grommet';
import { Header } from '@xtraplatform/core';
import { Globe } from 'grommet-icons';

const ClusterHeader = ({ dark, setDark }) => {
    return (
        <Header
            icon={<Globe />}
            label='Cluster'
            title='Cluster'
            actions={
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
            }
        />
    );
};

export default ClusterHeader;
