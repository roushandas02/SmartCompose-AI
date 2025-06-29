import React from 'react'
import { useState } from 'react'
import { Box, Button, CircularProgress, Container, FormControl, Input, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material';
import axios from 'axios';

function Home() {
    const [emailContent, setEmailContent] = useState('');
    const [tone, setTone] = useState('');
    const [customPrompt, setcustomPrompt] = useState('');
    const [generatedReply, setGeneratedReply] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSubmit = async () => {
        setLoading(true);
        setError('');
        try {
        const response = await axios.post("http://localhost:8080/api/email/generate", {
        emailContent,
        tone,
        customPrompt
        });
        //to make sure data sent is string
        setGeneratedReply(typeof response.data === 'string' ? response.data : JSON.stringify(response.data));
        } catch (error) {
        setError('Failed to generate eamil reply. Please try again');
        console.error(error);
        } finally {
        setLoading(false);
        }
    };

  return (
    <Container maxWidth="md" sx={{py:4}}>
      <Typography variant="h3" component="h1" gutterBottom
        sx={{
          fontWeight: 700,
          textAlign: 'center',
          color: 'primary.main',
        }}
      >
      SmartCompose AI
    </Typography>

    <Typography variant="subtitle1" component="p" gutterBottom
      sx={{
        textAlign: 'center',
        color: 'text.secondary',
        maxWidth: 500,
        mx: 'auto',
        mb: 4,
      }}
    >
    Generate smart email replies instantly with AI.
  </Typography>


      <Box sx={{ mx: 3 }}>
        <TextField 
          fullWidth
          multiline
          rows={6}
          variant='outlined'
          label="Original Email Content"
          value={emailContent || ''}
          onChange={(e) => setEmailContent(e.target.value)}
          sx={{ mb:2 }}/>

          <FormControl fullWidth sx={{ mb:2 }}>
            <InputLabel>Tone (Optional)</InputLabel>
            <Select
              value={tone || ''}
              label={"Tone (Optional)"}
              onChange={(e) => setTone(e.target.value)}>
                <MenuItem value="">None</MenuItem>
                <MenuItem value="professional">Professional</MenuItem>
                <MenuItem value="casual">Casual</MenuItem>
                <MenuItem value="friendly">Friendly</MenuItem>
            </Select>
          </FormControl>

          <TextField 
            fullWidth
            multiline
            rows={2}
            variant='outlined'
            label="Write any custom prompt (optional)"
            value={customPrompt || ''}
            onChange={(e) => setcustomPrompt(e.target.value)}
            sx={{ mb:2 }}/>

          <Button
            variant='contained'
            onClick={handleSubmit}
            disabled={!emailContent || loading}
            fullWidth>
            {loading ? <CircularProgress size={24}/> : "Generate Reply"}
          </Button>
      </Box>

      {error && (
        <Typography color='error' sx={{ mb:2 }}>
          {error}
        </Typography>
      )}

      {/* if reply generated then show */}
      {generatedReply && (
       <Box sx={{ mt: 3}}>
          <Typography variant='h6' gutterBottom>
            Generated Reply:
          </Typography>
          <TextField
            fullWidth
            multiline
            rows={6}
            variant='outlined'
            value={generatedReply || ''}
            inputProps={{ readOnly: true }}/>
        
        <Button
          variant='outlined'
          sx={{ mt: 2 }}
          // copy to clipboard
          onClick={() => navigator.clipboard.writeText(generatedReply)}>
            Copy to Clipboard
        </Button>
       </Box> 
      )}
    </Container>
  )
}

export default Home
