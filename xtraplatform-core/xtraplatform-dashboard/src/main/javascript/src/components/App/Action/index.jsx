import React from 'react';
import { Box, Button } from 'grommet';

const Action = () => {
    const onClick = () => {
        fetch('/tasks/reload-entities?ids=cshapes&types=services', {
            method: 'POST',
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
            })
            .catch((error) => {
                console.error(error);
            });
    };
    return (
        <Box overflow={{ vertical: 'auto' }} height={{ min: 'medium' }}>
            <Button primary label='Mach' alignSelf='center' margin='large' />
            <Button
                primary
                label='Reload Configuration'
                alignSelf='center'
                margin='large'
                onClick={onClick}
            />
        </Box>
    );
};

export default Action;
