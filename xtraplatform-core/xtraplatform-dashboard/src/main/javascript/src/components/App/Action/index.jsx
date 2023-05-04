import React from 'react';
import { Box, Button } from 'grommet';

const Action = () => {
    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Button primary label='Mach' alignSelf='center' margin='large' />
            <Button primary label='Reload Configuration' alignSelf='center' margin='large' />
        </Box>
    );
};

export default Action;
