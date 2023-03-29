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

const Details = ({ title, status }) => {
    const key = "cshapes"//wird normalerweise als prop übergeben
    const [dark, setDark] = useState(false);

    const size = useContext(ResponsiveContext);
    const selectedProvider = Object.keys(healthcheck).find((key) => key.includes(key));
    const art = selectedProvider
        ? selectedProvider.substring(selectedProvider.lastIndexOf('.') + 1)
        : '';
    const healthy = healthcheck[selectedProvider]?.healthy;

    return (
        <Grommet theme={theme} full themeMode={dark ? 'dark' : 'light'}>
            <Page>
                <AppBar>
                    <Text size='large'>{key.charAt(0).toUpperCase() + key.slice(1)}</Text>
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
                <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                <Box pad='none' background='content' flex={false}>
                    <TileGrid compact='small'> 
                   
    <Tile
        title=  {art.replace(/([a-z])([A-Z])/g, '$1 $2') + ': '} 
       
        status={
           healthy
        }
     
                key={key}
                isCompact={true}
             
    /> 

    </TileGrid>
    </Box>
    </Box>
</Grid>
            </PageContent>
            </Page>
        </Grommet>
        );
};

export default Details;

