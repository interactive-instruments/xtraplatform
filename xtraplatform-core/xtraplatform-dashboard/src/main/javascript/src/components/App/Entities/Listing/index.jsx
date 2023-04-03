import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { Box, ResponsiveContext } from 'grommet';

import { TileGrid } from '@xtraplatform/core';
import { Tile } from '../../Tests/Tile';

const EntitiesListing = ({}) => {
    const [currentID, setCurrentID] = useState(null);
    const [entities, setEntities] = useState({ providers: [] });

    useEffect(() => {
        fetch('entities')
            .then((response) => {
                console.log(response.status);
                return response.json();
            })
            .then((data) => {
                console.log(data);
                setEntities(data);
            })
            .catch((error) => console.log(error));
    }, []);

    return (
        <ResponsiveContext.Consumer>
            {(size) => (
                <>
                    {entities.providers.map((provider) => (
                        <Box fill='vertical' overflow={{ vertical: 'auto' }}>
                            <Box pad='none' background='content' flex={false}>
                                <TileGrid compact='small'>
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
                                </TileGrid>
                            </Box>
                        </Box>
                    ))}
                </>
            )}
        </ResponsiveContext.Consumer>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
