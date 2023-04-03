import React from 'react';
import { Box } from 'grommet';

import { Navigation } from '../';

const Page = ({ appName, menuRoutes, isLayer, isLayerActive, onClose, children }) => {
    return (
        <Box direction='row' fill>
            <Navigation
                title={appName}
                routes={menuRoutes}
                isLayer={isLayer}
                isLayerActive={isLayerActive}
                onClose={onClose}
            />
            {children}
        </Box>
    );
};

Page.displayName = 'Page';

Page.propTypes = {};

export default Page;
