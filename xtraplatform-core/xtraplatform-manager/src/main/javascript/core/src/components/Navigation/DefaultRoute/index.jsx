import React from 'react';
import PropTypes from 'prop-types';
import { Redirect } from 'react-router-dom';

const DefaultRoute = ({ defaultRoute, doNotRedirect, children }) => {
    return defaultRoute && defaultRoute.path && !doNotRedirect ? (
        <Redirect to={defaultRoute.path} />
    ) : (
        children
    );
};

DefaultRoute.propTypes = {
    defaultRoute: PropTypes.shape({ path: PropTypes.string }),
    doNotRedirect: PropTypes.bool,
    children: PropTypes.element,
};

DefaultRoute.defaultProps = {
    defaultRoute: null,
    doNotRedirect: false,
    children: null,
};

DefaultRoute.displayName = 'DefaultRoute';

export default DefaultRoute;
