import React from 'react';

import { Grommet } from 'grommet';
import { HashRouter as Router, Switch, Route } from 'react-router-dom';

import { Page, DefaultRoute, createTheme } from '@xtraplatform/core';
import EntitiesListing from './Entities/Listing';
import EntitiesDetails from './Entities/Details';
import UnsortedChecks from './Tests/UnsortedChecks';

const theme = createTheme();

const routes = [
    { menuLabel: 'Cluster', path: '/cluster', content: <UnsortedChecks /> },
    { menuLabel: 'Entities', path: '/entities', default: true, content: <EntitiesListing /> },
    { path: '/entities/:id', content: <EntitiesDetails /> },
];
const menuRoutes = routes.filter((route) => route.menuLabel);
const defaultRoute = routes.find((route) => route.default);

const App = () => {
    const dark = false;
    // const [dark, setDark] = useState(false);

    return (
        <Grommet theme={theme} full themeMode={dark ? 'dark' : 'light'}>
            <Router>
                <Switch>
                    <Route path='/' exact>
                        <DefaultRoute defaultRoute={defaultRoute}>
                            <Page appName='Dashboard' menuRoutes={menuRoutes} />
                        </DefaultRoute>
                    </Route>
                    {routes.map(({ path, content }) => (
                        <Route key={path} path={path} exact>
                            <Page appName='Dashboard' menuRoutes={menuRoutes}>
                                {content}
                            </Page>
                        </Route>
                    ))}
                </Switch>
            </Router>
        </Grommet>
    );
};

export default App;
