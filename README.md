# java-ai-playground
playground for java AI 

Manaul RAG flow

```
					User Question
                        │
                        ▼
      ChatClient.prompt().user(prompt)
                        │
                        ▼
          QuestionAnswerAdvisor
                        │
                        ▼
      mxbai-embed-large (Embedding Model)
                        │
                        ▼
              Question Vector
                        │
                        ▼
                 PGVector Search
                        │
                        ▼
          Top Matching Chunks
                        │
                        ▼
       Advisor Builds Final Prompt
                        │
                        ▼
          OllamaChatModel (Mistral)
                        │
                        ▼
              Generated Answer
                        │
                        ▼
                  .content()
```

---

# Improvements

	- Store Document Metadata
	- Duplicate Detection
	- Citations
	- Configurable Prompt Templates
	- Chat History Persistence
	- Document Management API
	- Similarity Score Endpoint
		```
			/similarity
			{
			  "score":0.91,
			  "decision":"RAG"
			}
		```
---
# Step Up

	```
		Transformer architecture
		Attention mechanism
		Tokenization
		Vector mathematics
		ANN indexes (HNSW, IVFFlat)
		Embedding dimensions
		Reranking
		Hybrid search
		Function Calling
		Agent frameworks
		Evaluation metrics
		Fine tuning vs RAG
		LoRA
		Quantization
		Context compression
	```