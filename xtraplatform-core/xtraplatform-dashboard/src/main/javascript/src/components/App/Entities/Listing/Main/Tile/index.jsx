import React from 'react';
import PropTypes from 'prop-types';

import { useHistory, useLocation } from 'react-router-dom';
import { Box, Text, Heading } from 'grommet';
import { Tile as Coretile, StatusIcon } from '@xtraplatform/core';

const getStatusIconType = (status) => {
    switch (status) {
        case 'Active':
        case true:
        case 'Reloading':
            return 'ok';
        case 'Disabled':
            return 'disabled';
        case 'Defective':
        case false:
            return 'critical';
        case 'Loading':
            return 'transit';
        default:
            return 'unknown';
    }
};

const getStatusText = (status) => {
    switch (status) {
        case 'Active':
        case true:
            return 'Online';
        case 'Disabled':
            return 'Offline';
        case 'Defective':
        case false:
            return 'Defective';
        case 'Loading':
            return 'Initializing';
        case 'Reloading':
            return 'Reloading';
        default:
            return 'Unknown';
    }
};

export const Tile = ({ title, status, id, label }) => {
    const history = useHistory();

    const iconSize = 'medium';

    const statusText = getStatusText(status);
    const statusIcon = (
        <StatusIcon
            value={getStatusIconType(status)}
            size={iconSize}
            a11yTitle={statusText}
            title={statusText}
        />
    );

    return (
        <Coretile
            align='start'
            direction='column'
            basis='auto'
            fill='horizontal'
            onClick={() => {
                if (id) {
                    const newPathname = history.location.pathname.includes('cluster')
                        ? `/cluster/${id}`
                        : `/entities/${id}`;
                    history.push({ pathname: newPathname });
                }
            }}
            selected={false}
            focusIndicator={false}
            background='background-front'
            hoverStyle='border'
            hoverColorIndex='accent-1'
            hoverBorderSize='large'
            pad='none'>
            {/* Card */}
            <Box fill='horizontal' textSize='small' pad='small'>
                <Box direction='row' justify='between' align='center' fill='horizontal'>
                    <Text
                        size='small'
                        weight='bold'
                        color='dark-4'
                        truncate
                        title={title}
                        margin={{ right: 'xsmall' }}
                        style={{ fontFamily: '"Roboto Mono", monospace' }}>
                        {title}
                    </Text>
                    <span title={statusText}>{statusIcon}</span>
                </Box>
                {label ? (
                    <Box
                        margin={{ top: 'top' }}
                        direction='row'
                        align='center'
                        justify='between'
                        textSize='small'>
                        <Heading level='4' truncate margin='none' title={label}>
                            {label}
                        </Heading>
                    </Box>
                ) : null}
            </Box>
        </Coretile>
    );
};

Tile.displayName = 'Tile';

Tile.propTypes = {
    setCurrentID: PropTypes.func.isRequired,
    title: PropTypes.string.isRequired,
    status: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
};

Tile.defaultProps = {};
