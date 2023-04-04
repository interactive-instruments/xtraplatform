import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

import { Box, ResponsiveContext } from 'grommet';

import { TileGrid, Content } from '@xtraplatform/core';
import { Tile } from './Tile';
import { useEntities } from '../../../hooks';

const EntitiesListing = ({}) => {
    const [currentID, setCurrentID] = useState(null);
    const entities = useEntities();

    return (
        <ResponsiveContext.Consumer>
            {(size) => (
                <>
                    <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                        <Box pad='none' background='content' flex={false}>
                            <TileGrid compact='small'>
                                {entities.providers.map((provider) => (
                                    <Tile
                                        title={provider.id}
                                        status={
                                            provider.status.charAt(0).toUpperCase() +
                                            provider.status.substring(1).toLowerCase()
                                        }
                                        setCurrentID={setCurrentID}
                                        currentID={currentID}
                                        id={provider.id}
                                        isCompact={size === 'small'}
                                    />
                                ))}
                            </TileGrid>
                        </Box>
                    </Box>
                </>
            )}
        </ResponsiveContext.Consumer>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
