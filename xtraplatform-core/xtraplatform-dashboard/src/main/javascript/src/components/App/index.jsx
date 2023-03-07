import React, { useState, useContext, useEffect } from "react";
import { Moon, Sun } from "grommet-icons";
import services from "./Tests/services";
import healthcheck from "./Tests/healthcheck";
import Sidebar from "./Tests/Sidebar";
import Tile from "./Tests/Tile";

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
} from "grommet";
import { deepMerge } from "grommet/utils";

const theme = deepMerge(grommet, {
  global: {
    colors: {
      brand: "#228BE6",
    },
    font: {
      family: "Roboto",
      size: "14px",
      height: "20px",
    },
  },
});

const AppBar = (props) => (
  <Header
    background="brand"
    pad={{ left: "medium", right: "small", vertical: "small" }}
    elevation="small"
    {...props}
  />
);

const CardTemplate = ({ title, id, status }) => {
  const size = useContext(ResponsiveContext);
  const selectedProvider = Object.keys(healthcheck).find(key =>
    key.includes(id)
  );
  const art =  selectedProvider ? selectedProvider.substring(selectedProvider.lastIndexOf(".") + 1) : "";
  const healthy = healthcheck[selectedProvider]?.healthy;
    
 

  return (
    
    <Box fill='vertical' overflow={{ vertical: 'auto' }}>
      <Box pad='none' background='content' flex={false}>
        <Heading level={2} margin="none">
          {title}
        </Heading>   
        <Paragraph maxLines={size === "small" ? 3 : undefined}>
          Art des Checks: {art.replace(/([a-z])([A-Z])/g, "$1 $2")} <br></br>
          Status: {status} <br></br>
          Healthy: {healthy?.toString().charAt(0).toUpperCase() + healthy?.toString().slice(1)}
        </Paragraph>
      </Box>
      </Box> /*
      <Box fill='vertical' overflow={{ vertical: 'auto' }}>
      <Box pad='none' background='content' flex={false}>
        <Heading level={2} margin="none">
          {title}
        </Heading>   
        <Tile
              align='start'
            direction='column'
            basis={'1/3'}
            fill={ false}
            onClick={() => history.push({ pathname: `${route}/${id}`, search: location.search })}
            selected={true}
            focusIndicator={false}
            background='background-front'
            hoverStyle='border'
            hoverColorIndex='accent-1'
            hoverBorderSize='large'
            pad='none'>
              <Text
              healthy
              />
        </Tile>
      </Box>
      </Box> */
  );
}; 

const App = () => {
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
    <Grommet theme={theme} full themeMode={dark ? "dark" : "light"}>
      <Page>
        <AppBar>
          <Text size="large">Checks</Text>
          <Button
            a11yTitle={dark ? "Switch to Light Mode" : "Switch to Dark Mode"}
            icon={dark ? <Moon /> : <Sun />}
            onClick={() => setDark(!dark)}
            tip={{
              content: (
                <Box
                  pad="small"
                  round="small"
                  background={dark ? "dark-1" : "light-3"}
                >
                  {dark ? "Switch to Light Mode" : "Switch to Dark Mode"}
                </Box>
              ),
              plain: true,
            }}
          />
        </AppBar>
        <PageContent>{/*
        <Sidebar isLayer={true} onClose={() => console.log("closed")} hideBorder={true} children={"Hallo"} /> */}
        <Grid columns="medium" gap="large" pad={{ bottom: "large" }}>
          {services.providers.map((provider) => (
  <CardTemplate title={provider.id.charAt(0).toUpperCase() + provider.id.slice(1)} 
                id={provider.id} 
                status={provider.status.charAt(0).toUpperCase() + provider.status.substring(1).toLowerCase()}
                />
))}          
          </Grid>
        </PageContent>
      </Page>
    </Grommet>
  );
};

export default App;
