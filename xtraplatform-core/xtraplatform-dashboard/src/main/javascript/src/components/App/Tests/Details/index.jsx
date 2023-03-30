import React, { useState, useContext, useEffect } from 'react';
import { Moon, Sun } from 'grommet-icons';
import services from '../services';
import healthcheck from '../healthcheck';
import Sidebar from '../Sidebar';
import { TileGrid } from '@xtraplatform/core';
import  { Tile }  from '../Tile';


import {
    Box,
    Button,
    Card,
    CardHeader,
    CardBody,
    Grid,
    grommet,
    Grommet,
    Header,
    Heading,
    Page,
    PageContent,
    Paragraph,
    ResponsiveContext,
    Text,
} from 'grommet';
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
    }
});

const AppBar = (props) => (
    <Header
        background='brand'
        pad={{ left: 'medium', right: 'small', vertical: 'small' }}
        elevation='small'
        {...props}
    />
);

const Details = (currentID) => {
    const [dark, setDark] = useState(false);
    const lowercasedID = currentID.currentID.toLowerCase();
    const selectedCheck = Object.keys(healthcheck).filter((key) => key.includes(lowercasedID));
console.log(selectedCheck);
    return (
        <Grommet theme={theme} full themeMode={dark ? 'dark' : 'light'}>
            <Page> 
                <AppBar>
                    <Text size='large'>{currentID !== null && currentID.currentID}</Text>
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
                {selectedCheck && selectedCheck.length > 0 ? ( 
                selectedCheck.map((check) => (
                <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                <Box pad='none' background='content' flex={false}>
                    <TileGrid compact='small'> 
                   
    <Tile
        title=  {check ? check.substring(check.lastIndexOf('.') + 1).replace(/([a-z])([A-Z])/g, '$1 $2') + ': ' : "No pending Checks"} 
       
        status={
            healthcheck[check]?.healthy
        }
     
                key={currentID !== null && currentID.currentID}
                isCompact={true}
             
    /> 

    </TileGrid>
    </Box>
    </Box>
                ))) : 
<Box fill='vertical' overflow={{ vertical: 'auto' }}>
                <Box pad='none' background='content' flex={false}>
                    <TileGrid compact='small'> 
                   
    <Tile
        title=  {"No pending Checks"}
       
       
     
                key={currentID !== null && currentID.currentID}
                isCompact={true}
             
    /> 

    </TileGrid>
    </Box>
    </Box>                
                }
</Grid>
            </PageContent>
            </Page>
        </Grommet>
        );
};

export default Details;

