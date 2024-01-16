package com.api.clinica.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @PostMapping
    public Produto salvar(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }


    @GetMapping
    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

}
