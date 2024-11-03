def criar_arquivo_2gb(nome_arquivo="dataset_500mb.txt"):
    # 1 GB = 1024 * 1024 * 1024 bytes
    tamanho_2gb = 500 * 1024 * 1024
    try:
        with open("shrek-historia.txt", "rb") as origem, open(nome_arquivo, "wb") as destino:
            tamanho_atual = 0
            dados = origem.read()

            if not dados:
                print("Arquivo de origem está vazio.")
                return
            
            while tamanho_atual < tamanho_2gb:
                destino.write(dados)
                tamanho_atual += len(dados)
            
            print(f"Arquivo '{nome_arquivo}' criado com sucesso com tamanho de 1 GB.")
    except IOError as e:
        print(f"Erro ao criar o arquivo: {e}")

if __name__ == "__main__":
    criar_arquivo_2gb()