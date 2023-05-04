import React from 'react';
import { Moon, Sun, Globe } from 'grommet-icons';
import { Box, Button } from 'grommet';
import { Header } from '@xtraplatform/core';
import Filter from '../Filter';

const AppBar = (props) => (
    <Header
        background='brand'
        style={{ width: '100%' }}
        pad={{ left: 'medium', right: 'small', vertical: 'small' }}
        {...props}
    />
);

const ListingHeader = ({ dark, setDark }) => {
    return (
        <>
            <Header
                icon={<Globe />}
                title='Entities'
                label='Entities'
                actions=<Button
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
            />
        </>
    );
};

export default ListingHeader;
