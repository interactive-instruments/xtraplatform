import React, { useState, useContext, useEffect } from 'react';
import Details from './Tests/Details';
import { Moon, Sun } from 'grommet-icons';
import services from './Tests/services';
import healthcheck from './Tests/healthcheck';
// import Sidebar from './Tests/Sidebar';
import { TileGrid, Sidebar } from '@xtraplatform/core';
import  { Tile }  from './Tests/Tile';

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

const App = () => {
    const [currentID, setCurrentID] = useState(null);
    const [dark, setDark] = useState(false);

    /*
  useEffect(() => {
    fetch(healthcheck)
    .then((response) => {
      console.log(response.status);
      return response.json();
    })
    .then((data) => {
      console.log(data)
    })
    .catch((error) => console.log(error));
  
  }, [])

  useEffect(() => {
    fetch(services)
    .then((response) => {
      console.log(response.status);
      return response.json();
    })
    .then((data) => {
      console.log(data)
    })
    .catch((error) => console.log(error));
  
  }, [])

  */

  return (
    <ResponsiveContext.Consumer>
        {(size) => {
            const isSmall =  size === 'small';

           if(currentID === null){ return (
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
                <Sidebar isLayer={false} children={"Hallo"} />
                <PageContent>
                    <Grid columns='medium' gap='large' pad={{ bottom: 'large' }}>
                        {services.providers.map((provider) => (
                                        <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                                        <Box pad='none' background='content' flex={false}>
                                            <TileGrid compact='small'> 
                                           
                            <Tile
                                title={provider.id.charAt(0).toUpperCase() + provider.id.slice(1)}
                               
                                status={
                                    provider.status.charAt(0).toUpperCase() +
                                    provider.status.substring(1).toLowerCase()
                                }
                                        setCurrentID={setCurrentID}
                                        currentID={currentID}
                                        id={provider.id.charAt(0).toUpperCase() + provider.id.slice(1)}
                                        isCompact={isSmall}
                                     
                            /> 
                   
                            </TileGrid>
                            </Box>
                            </Box>
                        ))}
                    </Grid>
                </PageContent>
            </Page>
        </Grommet>
        );
    } else {
      return <Details currentID={currentID}/>;
    }
  }}
</ResponsiveContext.Consumer>
);
};

export default App;
