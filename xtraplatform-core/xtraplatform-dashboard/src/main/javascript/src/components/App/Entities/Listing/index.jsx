import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

import { Box, ResponsiveContext } from 'grommet';

import { TileGrid, Content } from '@xtraplatform/core';
import Main from './Main';
import Header from './Header';

const EntitiesListing = ({}) => {
    return <Content header={<Header />} main={<Main />} />;
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
