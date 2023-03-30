import React, {useState} from 'react';
import PropTypes from 'prop-types';

import { Box, Text, Heading } from 'grommet';
import { Tile as Coretile, StatusIcon, TaskProgress } from '@xtraplatform/core';

const getStatusIconType = status => {
    switch (status) {
        case "Active":
            case true:      
        case "Reloading":              
            return "ok"
        case "Disabled":            
            return "disabled"
        case "Defective":
            case false:            
            return "critical"
        case "Loading":           
            return "transit"    
        default:
            return "unknown";
    }
}

const getStatusText = status => {
    switch (status) {
        case "Active":
            case true:         
            return "Online"
        case "Disabled":            
            return "Offline"
        case "Defective":
            case false:            
            return "Defective"
        case "Loading":         
            return "Initializing"
        case "Reloading":            
            return "Reloading"    
        default:
            return "Unknown";
    }
}



export const Tile = ({
    title,
    label,
    enabled,
    status,
    id,
    currentID,
    setCurrentID,
    message,
    progress,
    hasProgress,
    hasBackgroundTask,
    isSelected,
    isCompact,
}) => {
   const iconSize = 'medium';

    const statusText = getStatusText(status);
    const statusIcon = (
        <StatusIcon
            value= {getStatusIconType(status)}
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
            fill="horizontal" 
            onClick={() => {
                if (currentID === null) {
                  setCurrentID(id);
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
                        size={'small'}
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
            </Box>
        </Coretile>
    );
};

Tile.displayName = 'Tile';

Tile.propTypes = {
    onClick: PropTypes.func,
    basis: PropTypes.string,
    background: PropTypes.string,
    fill: PropTypes.string,
    pad: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};

Tile.defaultProps = {
    onClick: null,
    basis: null,
    background: null,
    fill: null,
    pad: null,
};


