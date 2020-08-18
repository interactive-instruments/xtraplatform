import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import { Anchor } from 'grommet';

const StyledAnchor = styled(Anchor)`
    text-decoration: none;
    padding: 0;
    ${(props) =>
        props.iconSize && `height: ${props.theme.icon.size[props.iconSize]};`}
`;

const LinkAnchor = ({ icon, navigate, ...rest }) => {
    const iconSize = (icon && icon.props.size) || 'medium';

    return (
        <StyledAnchor
            {...rest}
            icon={icon}
            iconSize={iconSize}
            onClick={(event) => {
                event.preventDefault();
                navigate();
                event.currentTarget.blur();
            }}
        />
    );
};

LinkAnchor.displayName = 'LinkAnchor';

LinkAnchor.propTypes = {
    icon: PropTypes.element,
    navigate: PropTypes.func.isRequired,
};

LinkAnchor.defaultProps = {
    icon: null,
};

const NavLink = ({ to, ...rest }) => {
    return <Link {...rest} to={to} component={LinkAnchor} />;
};

NavLink.displayName = 'NavLink';

NavLink.propTypes = {
    to: PropTypes.oneOfType([PropTypes.string, PropTypes.object]).isRequired,
};

export default NavLink;
