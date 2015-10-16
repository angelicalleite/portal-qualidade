-- Alteração no banco de dados do explorador para a adição de flags necessárias para a execução da ferramenta de qualidade de dados (Validação ortográfica)
ALTER TABLE public.occurrence_raw ADD dq_validacao_GBIF boolean default false;
ALTER TABLE public.occurrence_raw ADD dq_validacao_listaFloraBrasil boolean default false; 

CREATE INDEX dq_validacao_gbif_idx ON public.occurrence_raw(dq_validacao_gbif);
CREATE INDEX dq_validacao_listaflorabrasil_idx ON public.occurrence_raw(dq_validacao_listaflorabrasil);

CREATE INDEX dwcaid_idx ON public.occurrence_raw(dwcaid);

CREATE INDEX taxon_rank_idx ON public.occurrence_raw(taxonrank);

REINDEX TABLE public.occurrence_raw;
