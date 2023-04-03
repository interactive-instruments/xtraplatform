import React, { useContext } from 'react';
import PropTypes from 'prop-types';

import { Box, ThemeContext } from 'grommet';

import { Sidebar } from '../Layout/Sidebar';
import NavigationHeader from './Header';
import NavigationMenu from './Menu';

export { default as Page } from './Page';

export const Navigation = ({
    title,
    logo,
    routes,
    onClose,
    isLayer,
    isLayerActive,
    showMenu,
    children,
}) => {
    const theme = useContext(ThemeContext);
    const color = theme.normalizeColor(theme.navigation.color, theme.navigation.dark);
    const bgColor = theme.navigation.background;

    if (isLayer && !isLayerActive) {
        return null;
    }

    return (
        <Sidebar isLayer={isLayer} hideBorder onClose={onClose}>
            <Box fill='vertical' background={bgColor} color={color}>
                <NavigationHeader
                    isLayer={isLayer}
                    onClose={onClose}
                    title={title}
                    logo={logo}
                    color={color}
                />
                {showMenu ? (
                    <Box justify='around' fill='vertical'>
                        <NavigationMenu routes={routes} onClick={onClose} />
                        {children}
                    </Box>
                ) : (
                    children
                )}
            </Box>
        </Sidebar>
    );
};

Navigation.displayName = 'Navigation';

Navigation.propTypes = {
    title: PropTypes.string,
    logo: PropTypes.string,
    routes: PropTypes.arrayOf(PropTypes.object),
    onClose: PropTypes.func,
    isLayer: PropTypes.bool,
    isLayerActive: PropTypes.bool,
    showMenu: PropTypes.bool,
    children: PropTypes.element,
};

Navigation.defaultProps = {
    title: null,
    logo: null,
    routes: [],
    onClose: null,
    isLayer: false,
    isLayerActive: false,
    showMenu: true,
};
