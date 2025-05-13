import React from 'react';
import {
  Box,
  Paper,
  Typography,
  Chip,
  List,
  ListItem,
  ListItemText,
  Alert,
  Divider,
} from '@mui/material';
import MessageIcon from '@mui/icons-material/Message';
import type { SmsResponse } from '../types/api.types';

interface SmsResultsProps {
  result: SmsResponse | null;
  error: string | null;
}

const SmsResults: React.FC<SmsResultsProps> = ({ result, error }) => {
  if (error) {
    return (
      <Paper elevation={3} sx={{ p: 4, maxWidth: 600, mx: 'auto', mt: 3 }}>
        <Alert severity="error">
          <Typography variant="body1">Error sending SMS:</Typography>
          <Typography variant="body2">{error}</Typography>
        </Alert>
      </Paper>
    );
  }

  if (!result) {
    return null;
  }

  return (
    <Paper elevation={3} sx={{ p: 4, maxWidth: 600, mx: 'auto', mt: 3 }}>
      <Typography variant="h6" gutterBottom>
        SMS Split Results
      </Typography>
      
      <Box sx={{ mt: 2, mb: 3 }}>
        <Typography variant="body2" color="text.secondary" gutterBottom>
          Original Message ({result.totalCharacters} characters)
        </Typography>
        <Typography variant="body1" sx={{ fontStyle: 'italic' }}>
          "{result.originalMessage}"
        </Typography>
      </Box>
      
      <Divider />
      
      <Box sx={{ mt: 3, mb: 2, display: 'flex', gap: 2, alignItems: 'center' }}>
        <Chip 
          label={`${result.totalParts} part${result.totalParts !== 1 ? 's' : ''}`}
          color="primary"
          icon={<MessageIcon />}
        />
        <Typography variant="body2" color="text.secondary">
          Split into {result.totalParts} message{result.totalParts !== 1 ? 's' : ''}
        </Typography>
      </Box>
      
      <List>
        {result.parts.map((part, index) => (
          <ListItem key={index} sx={{ backgroundColor: index % 2 === 0 ? 'grey.50' : 'white' }}>
            <ListItemText
              primary={`Part ${index + 1}`}
              secondary={
                <Typography variant="body2" sx={{ wordBreak: 'break-word' }}>
                  {part}
                </Typography>
              }
            />
            <Chip 
              label={`${part.length} chars`} 
              size="small" 
              variant="outlined"
              sx={{ ml: 2 }}
            />
          </ListItem>
        ))}
      </List>
    </Paper>
  );
};

export default SmsResults;